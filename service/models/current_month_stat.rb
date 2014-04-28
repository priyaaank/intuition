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
  end

end