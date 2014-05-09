class CurrentMonthStat

  attr_accessor :recommended_budget, :actual_budget, :savings_rate, :current_expense_total, :current_transaction_count, :category_stats

  def initialize(user, past_month_stats)
    expenditures = past_month_stats.map(&:total_amount_spent)
    @recommended_budget = (expenditures.sum/expenditures.count).round(0)
    @actual_budget = @recommended_budget
    current_month_transactions = user.transactions.for_year_and_month(Date.today.year, Date.today.month)
    @current_transaction_count = current_month_transactions.count
    @category_stats = CategoryStat.category_stats_from(current_month_transactions)
    @current_expense_total = current_month_transactions.map(&:price).sum
    number_of_days_in_current_month = Date.civil(Date.today.year, Date.today.month, -1).day
    day_today = Date.today.day
    @savings_rate = ((((@actual_budget/number_of_days_in_current_month) * day_today) - (@current_expense_total))/day_today).round(2)
    populate_category_breakup(past_month_stats)
  end

  private

  def populate_category_breakup past_month_stats
    total_amount_spent_in_past = past_month_stats.sum {|c| c.total_amount_spent }
    total_amount_spent_per_category = {}
    past_month_stats.inject(total_amount_spent_per_category) {|j, m| m.category_stats.inject(j) {|h,k| h[k.category_name] = ((h[k.category_name]||0) + k.total_amount_spent); h }}
    percentages = {}
    total_amount_spent_per_category.each {|category_name, total_amt| percentages[category_name] = (total_amt/total_amount_spent_in_past).round(2) }
    @category_stats.each { |category| category.expected_expense = (@actual_budget * percentages[category.category_name]).round(2) }

    #This misses the categories that we have not spent on this month. Also, we need to calculate delta due to percentage error due to inclusion of those categories
    # it is currently ignored right now.
  end

end