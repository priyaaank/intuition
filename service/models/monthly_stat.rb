class MonthlyStat

  attr_accessor :month, :year, :total_amount_spent, :total_amount_saved, :total_number_of_transactions, :saving_rate, :category_stats

  def initialize(month, year, user)
    @month = month
    @year = year
    @category_stats = []
    populate_stats_for_user (user)
  end

  def self.monthly_stats_for_last_months(user, month_count = 18)
    (1..month_count).collect do |month_in_past|
      date_in_past = Date.today<<month_in_past
      MonthlyStat.new(date_in_past.month, date_in_past.year, user)
    end
  end

  private

  def populate_stats_for_user user
    transactions = user.transactions.for_year_and_month(@year, @month)
    @total_amount_spent = transactions.sum(:price)
    @total_number_of_transactions = transactions.count
    @category_stats = CategoryStat.category_stats_from(transactions)
  end

end