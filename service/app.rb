require "sinatra"
require "sinatra/activerecord"
require './models/transaction'

set :database, "sqlite3:db/intuition.db"

post '/transaction/create' do

end

get '/merchant/lookup/:name' do

end