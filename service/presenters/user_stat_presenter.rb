class UserStatPresenter

  attr_accessor :historic_stats, :current_month_stats, :overall_stats

  def initialize(user, past_month_count)
    @user = user
    @month_count = past_month_count
    @historic_stats = MonthlyStat.monthly_stats_for_last_months(@user, @month_count)
    @current_month_stats = CurrentMonthStat.new(user, @historic_stats)
    @overall_stats = OpenStruct.new
    expenditure_list = @historic_stats.map(&:total_amount_spent)
    @overall_stats.maximum_monthly_expenditure  = expenditure_list.max
    @overall_stats.minimum_monthly_expenditure  = expenditure_list.min
    @overall_stats.average_monthly_expenditure  = expenditure_list.sum/expenditure_list.count
  end

  def as_json(opts = {})

    {
        "user" => @user.username,
        "historic" => @historic_stats,
        "current_month" => @current_month_stats,
        "overall" => @overall_stats.marshal_dump
    }

  end

end