require "sinatra"
require "sinatra/activerecord"
Dir["./models/**/*.rb"].each { |f| require f }
Dir["./extensions/**/*.rb"].each { |f| require f }
Dir["./presenters/**/*.rb"].each { |f| require f }

set :database, "sqlite3:db/intuition.db"

export_file = File.open("./transactions_export.csv","w")
export_file.write "Unique id, Merchant Id, Merchant Name, Price, Transaction Date, Transaction Month, Transaction Year, Transaction Day, User Name, Category\n"
Transaction.all.each do |t|
  transaction_date = t.transaction_date.strftime("%d")
  transaction_month = t.transaction_date.strftime("%m")
  transaction_year = t.transaction_date.strftime("%Y")
  transaction_day = t.transaction_date.strftime("%A")
  export_file.write "#{t.id}, #{t.merchant_id}, #{t.merchant_name}, #{t.price}, #{transaction_date}, #{transaction_month}, #{transaction_year}, #{transaction_day}, #{t.user.username}, #{t.category.name}\n"
end
export_file.close
