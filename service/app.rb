["ostruct", "sinatra", "sinatra/activerecord", Dir["./models/**/*.rb"], Dir["./extensions/**/*.rb"], Dir["./presenters/**/*.rb"]].flatten.each {|f| require f }
#set :database, "sqlite3:db/intuition.db"

db = URI.parse(ENV['DATABASE_URL'] || 'postgres:///localhost/mydb')

ActiveRecord::Base.establish_connection(
    :adapter  => db.scheme == 'postgres' ? 'postgresql' : db.scheme,
    :host     => db.host,
    :username => db.user,
    :password => db.password,
    :database => db.path[1..-1],
    :encoding => 'utf8'
)


get '/transaction/new' do
  @users = User.all.to_a
  erb :new_transaction
end


post '/transaction/create' do
  user_id = params[:username]
  merchant_name = params[:merchantName]
  merchant_id = params[:merchantId]
  transaction_amount = params[:transactionAmount].to_f
  user = User.find_by_id(user_id)
  puts "*"*100
  puts user.inspect
  puts merchant_name
  puts merchant_id
  puts transaction_amount
  puts "*"*100
  unknown_category = user.categories.where(:name => Category::Type::Uncategorized).first
  trxnStatus = 500
  unless user.nil? || merchant_name.nil? || merchant_id.nil? || transaction_amount.nil?
    puts "Creating Transaction!!"
    trxnStatus = user.transactions.create!(:merchant_name => merchant_name, :merchant_id => merchant_id, :transaction_date => Date.today, :price => transaction_amount, :category => unknown_category)
  end
  statusCode = trxnStatus ? 200 : 500
  status statusCode
end

get '/merchant/lookup/:name' do

end

get '/user/login/:username' do
  username = params[:username]
  created_user = User.create_user_if_doesnt_exists_for(username)
  BaseExpenses.new(created_user).create_expenses if created_user.transactions.empty?

  status 200
  body ''
end

get '/user/:username/transactions/page/:number' do
  user = User.find_by_username(params[:username])
  unless user.nil?
    user.transactions.order(:id => :desc)
  end
end

get '/user/:username/transactions/latest' do
  user = User.find_by_username(params[:username])
  unless user.nil?
    transaction = user.transactions.order(:id => :desc).first
  end
  response_for_latest_transaction transaction, user
end

get '/user/:username/transactions/:number_of_months/months/categorize' do
  user = User.find_by_username(params[:username])
  number_of_months = params[:number_of_months]
  response = {"categories" => [], "user" => params[:username]}.to_json
  unless user.nil?
    response = categorized_response_for user, user.transactions.past_months(number_of_months.to_i)
  end
  response
end

get '/user/:username/transactions/current/month/categorize' do
  user = User.find_by_username(params[:username])
  response = {"categories" => [], "user" => params[:username]}.to_json
  unless user.nil?
    response = categorized_response_for user, user.transactions.for_current_month
  end
  response
end

get '/user/:username/transactions/:num/months' do
  user = User.find_by_username(params[:username])
  month_count = (params[:num].downcase == "current") ? -1 : params[:num].to_i
  transaction_response_for_past_months(month_count, user)
end

put '/user/:username/transactions/update' do
  user = User.find_by_username(params[:username])
  request_body = JSON.parse(request.body.read)
  transaction_ids = request_body["transaction_ids"]
  new_category_id = request_body["category_id"]
  user.transactions.where(:id => transaction_ids).update_all(:category_id => new_category_id)
  transaction_response_for_past_months(2, user)
end

get '/user/:username/categories' do
  user = User.find_by_username(params[:username])
  response = OpenStruct.new
  unless user.nil?
    response.user = user.username
    response.categories = user.categories.collect{|category| UserCategory.new(category) }
  end
  response.to_json
end

post '/user/:username/category/new' do

  user = User.find_by_username(params[:username])
  request_body = JSON.parse(request.body.read)
  new_category_name = request_body["category_name"]
  user.categories.where(["lower(name) = ?", new_category_name.downcase]).first || user.categories.create(name: new_category_name)
  response = OpenStruct.new
  unless user.nil?
    response.user = user.username
    response.categories = user.categories.collect{|category| UserCategory.new(category) }
  end
  response.to_json
end

put '/user/:username/category/:id' do
  user = User.find_by_username(params[:username])
  request_body = JSON.parse(request.body.read)
  new_category_name = request_body["category_name"]
  category_to_update = user.categories.find_by_id(params[:id])
  category_to_update.name = new_category_name
  category_to_update.save!
  UserCategory.new(category_to_update).to_json
end

delete '/user/:username/category/:id' do
  user = User.find_by_username(params[:username])
  category = user.categories.find_by_id(params[:id])
  unless category.nil?
    unknown_category = user.categories.where(["lower(name) = ?", Category::Type::Uncategorized.downcase]).first
    category.transactions.update_all(:category_id => unknown_category.id)
    category.destroy
  end
  response = OpenStruct.new
  unless user.nil?
    response.user = user.username
    response.categories = user.categories.collect{|category| UserCategory.new(category) }
  end
  response.to_json
end

get '/user/:username/monthly_budget/average' do
  user = User.find_by_username(params[:username])
  AutoBudgetPresenter.new(user, user.transactions.past_months(18).monthly_sum).to_json
end

post '/user/:username/transactions/update/category/:category_id' do
  user = User.find_by_username(params[:username])
  new_category_id = User.find_by_username(params[:category_id])
  request_body = JSON.parse(request.body.read)
  transaction_ids = request_body["transaction_ids"]
  user.transactions.where(:id => transaction_ids).update_all(:category_id => new_category_id)
  status 200
  body ''
end

get '/user/:username/stats/last/:number/months' do
  user = User.find_by_username(params[:username])
  number_of_months = params[:number].to_i
  UserStatPresenter.new(user, number_of_months).to_json
end

private

def transaction_responses_for user, transactions
  response = OpenStruct.new
  response.transactions = transactions.collect {|transaction| TransactionPresenter.new(transaction) }
  response.user = user.username
  response.start_date = Date.today
  response.end_date = Date.today
  response.to_json
end

def categorized_response_for user, transactions
  response = OpenStruct.new
  response.categories = categories_data_for(user, transactions)
  response.user = user.username
  response.total_spending = response.categories.sum(&:price_total)
  response.to_json
end

def response_for_latest_transaction transaction, user
  response = OpenStruct.new
  total_money_spent = user.transactions.for_year_and_month(Date.today.year, Date.today.month).map(&:price).sum
  recommended_budget = UserStatPresenter.new(user, 18).current_month_stats.recommended_budget
  saving_rate = ((((recommended_budget/Date.civil(Date.today.year, Date.today.month, -1).day) * Date.today.day) - (total_money_spent))/Date.today.day).round(2)
  saving_rate = 0 if saving_rate < 0
  response.total_money_spent = total_money_spent
  response.budget = recommended_budget
  response.transaction = TransactionPresenter.new(transaction)
  response.saving_rate = saving_rate
  response.to_json
end

def categories_data_for(user, transactions)
  category_wise_price_totals = transactions.category_wise_sum
  category_wise_counts = transactions.categorized.count
  user.categories.collect(&:name).collect do |category|
    TransactionCategory.new(category, category_wise_counts[category], category_wise_price_totals[category])
  end
end

def transaction_response_for_past_months(month_count, user)
  response = {"categories" => [], "user" => params[:username]}.to_json
  unless user.nil?
    user.transactions.recategorize(user)
    transactions = month_count == -1 ? user.transactions.for_current_month : user.transactions.past_months(month_count)
    response = transaction_responses_for user, transactions
  end
  response
end
