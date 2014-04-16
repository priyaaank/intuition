require "sinatra"
require "sinatra/activerecord"
require './models/transaction'
require './models/category'
require './models/budget'
require './models/user'
require './models/data/base_expenses'
require './extensions/open_struct'
require './presenters/transaction_category'
require './presenters/user_category'

set :database, "sqlite3:db/intuition.db"

post '/transaction/create' do

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
    user.transactions.order_by(:transaction_date => :desc)
  end
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
  new_category = user.categories.where(["lower(name) = ?", new_category_name.downcase]).first || user.categories.create(name: new_category_name)
  UserCategory.new(new_category).to_json
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
  user.categories.find_by_id(params[:id]).destroy
  status 200
  body ''
end

private

def categorized_response_for user, transactions
  response = OpenStruct.new
  response.categories = categories_data_for(user, transactions)
  response.user = user.username
  response.total_spending = response.categories.sum(&:price_total)
  response.to_json
end

def categories_data_for(transactions)
  category_wise_price_totals = transactions.category_wise_sum
  category_wise_counts = transactions.categorized.count
  user.categories.collect(&:name).collect do |category|
    TransactionCategory.new(category, category_wise_counts[category], category_wise_price_totals[category])
  end
end
