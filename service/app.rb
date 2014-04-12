require "sinatra"
require "sinatra/activerecord"
require './models/transaction'
require './models/user'


set :database, "sqlite3:db/intuition.db"

post '/transaction/create' do

end

get '/merchant/lookup/:name' do

end

get '/user/login/:username' do
  username = params[:username]
  User.create_user_if_doesnt_exists_for(username)

  status 200
  body ''
end