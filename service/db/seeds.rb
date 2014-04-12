require 'date'
require './models/data/base_expenses'

#User David's yearly expenses
david = User.create_user_if_doesnt_exists_for("David")
BaseExpenses.new(david).create_expenses