require "sinatra"
require "sinatra/activerecord"
require './models/transaction'
require './models/user'
require './models/data/base_expenses'


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