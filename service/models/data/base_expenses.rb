require './models/data/expense_date_generator'
require './models/data/fake_expense'

class BaseExpenses

  def initialize(user)
    @user = user
  end

  def create_expenses
    utilities = @user.categories.find_by_name(Category::Type::UTILITIES)
    food = @user.categories.find_by_name(Category::Type::FOOD)
    fees_and_charges = @user.categories.find_by_name(Category::Type::FEES_AND_CHARGES)
    investments = @user.categories.find_by_name(Category::Type::INVESTMENTS)
    health = @user.categories.find_by_name(Category::Type::HEALTH)
    shopping = @user.categories.find_by_name(Category::Type::SHOPPING)
    travel = @user.categories.find_by_name(Category::Type::TRAVEL)

    FakeExpense.new([30, 150], "./data/samples/utilities.txt", 24, "House utilities expenses", @user, utilities).create_expenses
    FakeExpense.new([5, 15], "./data/samples/food.txt", 950, "Daily food expenses", @user, food).create_expenses
    FakeExpense.new([1, 5], "./data/samples/feesAndCharges.txt", 20, "Fees and charges expenses", @user,fees_and_charges).create_expenses
    FakeExpense.new([300, 600], "./data/samples/investments.txt", 8, "Investments", @user,investments).create_expenses
    FakeExpense.new([300, 1000], "./data/samples/health.txt", 3, "Health and medical needs", @user, health).create_expenses
    FakeExpense.new([50, 150], "./data/samples/shopping.txt", 20, "Monthly grocery shopping", @user, shopping).create_expenses
    FakeExpense.new([300, 1500], "./data/samples/shopping.txt", 5, "Expensive equipment shopping", @user, shopping).create_expenses
    FakeExpense.new([5, 50], "./data/samples/travel.txt", 250, "Local and ground travel", @user, travel).create_expenses
    FakeExpense.new([450, 800], "./data/samples/travel.txt", 20, "Airtravel and hotels", @user, travel).create_expenses
  end

end