require './models/data/expense_date_generator'
require './models/data/fake_expense'

class BaseExpenses

  def initialize(user)
    @user = user
  end

  def create_expenses
    FakeExpense.new([30, 150], "./data/samples/utilities.txt", 24, "House utilities expenses", @user).create_expenses
    FakeExpense.new([5, 15], "./data/samples/food.txt", 950, "Daily food expenses", @user).create_expenses
    FakeExpense.new([1, 5], "./data/samples/feesAndCharges.txt", 20, "Fees and charges expenses", @user).create_expenses
    FakeExpense.new([300, 600], "./data/samples/investments.txt", 8, "Investments", @user).create_expenses
    FakeExpense.new([300, 1000], "./data/samples/health.txt", 3, "Health and medical needs", @user).create_expenses
    FakeExpense.new([50, 150], "./data/samples/shopping.txt", 20, "Monthly grocery shopping", @user).create_expenses
    FakeExpense.new([300, 1500], "./data/samples/shopping.txt", 5, "Expensive equipment shopping", @user).create_expenses
    FakeExpense.new([5, 50], "./data/samples/travel.txt", 250, "Local and ground travel", @user).create_expenses
    FakeExpense.new([450, 800], "./data/samples/travel.txt", 20, "Airtravel and hotels", @user).create_expenses
  end

end