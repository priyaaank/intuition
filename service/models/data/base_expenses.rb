require './models/data/expense_date_generator'
require './models/data/fake_expense'

class BaseExpenses

  def initialize(user)
    @user = user
  end

  def create_expenses
    household = @user.categories.find_by_name(Category::Type::Household)
    fuel_and_transport = @user.categories.find_by_name(Category::Type::FuelAndTransport)
    shopping = @user.categories.find_by_name(Category::Type::Shopping)
    medical = @user.categories.find_by_name(Category::Type::Medical)
    recreation = @user.categories.find_by_name(Category::Type::Recreation)
    food_and_drinks = @user.categories.find_by_name(Category::Type::FoodAndDrinks)
    dining_out = @user.categories.find_by_name(Category::Type::DiningOut)
    holiday = @user.categories.find_by_name(Category::Type::Holiday)
    investment = @user.categories.find_by_name(Category::Type::Investment)
    fees_and_charges = @user.categories.find_by_name(Category::Type::FeesAndCharges)
    mortgage = @user.categories.find_by_name(Category::Type::Mortgage)
    uncategorized = @user.categories.find_by_name(Category::Type::Uncategorized)


    #Daily expenses
    daily_expense_routine = DailyExpenseDateGenerator.new(20, 20)
    FakeExpense.new([8, 15], "./data/samples/food.txt", "Daily food", @user, food_and_drinks, daily_expense_routine).create_expenses
    FakeExpense.new([10, 18], "./data/samples/travel.txt", "Daily travel", @user, fuel_and_transport, daily_expense_routine).create_expenses

    #Monthly expenses
    household_routine = MonthlyExpenseDateGenerator.new(0, 20, 2)
    FakeExpense.new([50, 80], "./data/samples/household.txt", "Monthly utility bills", @user, household, household_routine).create_expenses
    investment_routine = MonthlyExpenseDateGenerator.new(0, 20, 2)
    FakeExpense.new([200, 500], "./data/samples/investments.txt", "Monthly investments", @user, investment, investment_routine).create_expenses
    grocery_shopping_routine = MonthlyExpenseDateGenerator.new(20, 20, 4)
    FakeExpense.new([20, 50], "./data/samples/groceries.txt", "Weekly grocery shopping", @user, household, grocery_shopping_routine).create_expenses
    apparel_shopping = MonthlyExpenseDateGenerator.new(20, 20, 1)
    FakeExpense.new([80, 100], "./data/samples/shopping.txt", "Monthly apparel shopping", @user, shopping, apparel_shopping).create_expenses
    recreation_routine = MonthlyExpenseDateGenerator.new(10, 20, 4)
    FakeExpense.new([50, 100], "./data/samples/recreation.txt", "Monthly entertainment", @user, recreation, recreation_routine).create_expenses
    dining_out_routine = MonthlyExpenseDateGenerator.new(20, 20, 1)
    FakeExpense.new([50, 100], "./data/samples/dining.txt", "Monthly dining out", @user, dining_out, dining_out_routine).create_expenses
    house_mortgage_routine = MonthlyExpenseDateGenerator.new(10, 20, 1)
    FakeExpense.new([800, 850], "./data/samples/mortgage.txt", "Monthly mortgage", @user, mortgage, house_mortgage_routine).create_expenses

    #Yearly expenses
    holiday_routine = EveryFewMonthsExpenseDateGenerator.new(10, 20, 6, 1)
    FakeExpense.new([500, 1000], "./data/samples/holiday.txt", "Bi-Yearly holiday", @user, holiday, holiday_routine).create_expenses
    medical_routine = EveryFewMonthsExpenseDateGenerator.new(20, 20, 5, 1)
    FakeExpense.new([500, 800], "./data/samples/health.txt", "Yearly health checkup and illness", @user, medical, medical_routine).create_expenses
    fees_and_charges_routine = EveryFewMonthsExpenseDateGenerator.new(25, 20, 3, 5)
    FakeExpense.new([2, 8], "./data/samples/feesAndCharges.txt", "Miscellaneous fees and charges", @user, fees_and_charges, fees_and_charges_routine).create_expenses

    #Sporadic expenses

    #FakeExpense.new([30, 150], "./data/samples/utilities.txt", 24, "House utilities expenses", @user, utilities).create_expenses
    #FakeExpense.new([5, 15], "./data/samples/food.txt", 950, "Daily food expenses", @user, food).create_expenses
    #FakeExpense.new([1, 5], "./data/samples/fees_and_charges.txt", 20, "Fees and charges expenses", @user,fees_and_charges).create_expenses
    #FakeExpense.new([300, 600], "./data/samples/investments.txt", 8, "Investments", @user,investments).create_expenses
    #FakeExpense.new([300, 1000], "./data/samples/health.txt", 3, "Health and medical needs", @user, health).create_expenses
    #FakeExpense.new([50, 150], "./data/samples/shopping.txt", 20, "Monthly grocery shopping", @user, shopping).create_expenses
    #FakeExpense.new([300, 1500], "./data/samples/shopping.txt", 5, "Expensive equipment shopping", @user, shopping).create_expenses
    #FakeExpense.new([5, 50], "./data/samples/travel.txt", 250, "Local and ground travel", @user, travel).create_expenses
    #FakeExpense.new([450, 800], "./data/samples/travel.txt", 20, "Airtravel and hotels", @user, travel).create_expenses

    create_monthly_budgets_for_user(@user)
  end

  private

  def create_monthly_budgets_for_user(user)
    oldest_transaction = user.transactions.oldest
    start_year = oldest_transaction.transaction_date.year
    start_month = oldest_transaction.transaction_date.month
    current_month = Date.today.month
    current_year = Date.today.year

    number_of_months_in_past = ((current_year*12 + current_month) - (start_year*12 + start_month))
    (1..number_of_months_in_past).each do |month|
      past_date = Date.today<<month
      transactions = @user.transactions.until_year_and_month(past_date.year, past_date.month)
      month_count =  ((past_date.year*12 + past_date.month) - (start_year*12 + start_month))
      total_spending = transactions.map(&:price).sum
      average_budget = (total_spending > 0 && month_count > 0) ? total_spending/month_count : total_spending
      average_budget = (average_budget + (average_budget * (0.15))).round(2)
      #average_budget = (transactions.map(&:price).sum / month_count)
      #average_budget = transactions.map(&:price).sum if average_budget <= 0
      (user.budgets.where(:month => past_date.month, :year => past_date.year).first || user.budgets.create(:month => past_date.month, :year => past_date.year)).update(:amount => average_budget)
    end
  end

end