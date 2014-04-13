require "sinatra"
require "sinatra/activerecord"
require './models/transaction'
require './models/user'
require './models/data/base_expenses'
require 'ostruct'

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

get '/user/:username/transactions/categorize' do
  user = User.find_by_username(params[:username])
  transactions = []
  unless user.nil?
    transactions = user.transactions.categorized.count
  end
  categorized_response transactions, user
end

private

def categorized_response transactions, user
  response = OpenStruct.new
  response.categories = transactions
  response.user = user.username
  response.to_json
end


class OpenStruct
  def to_json
    table.to_json
  end
end