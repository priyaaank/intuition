class AutoBudgetPresenter

  attr_accessor :user, :min_monthly_expenditure, :max_monthly_expenditure, :avg_monthly_expenditure, :monthly_spending

  def initialize(user, budget_average = {})
    @user = user
    @monthly_spending = budget_average
    @min_monthly_expenditure = @monthly_spending.values.min
    @max_monthly_expenditure = @monthly_spending.values.max
    @average_monthly_expenditure = @monthly_spending.values.reduce(:+)/@monthly_spending.values.size
  end

  def as_json(options = {})
    {
      "expenditure" => {
          "average" => @average_monthly_expenditure.round(2),
          "mininum" => @min_monthly_expenditure.round(2),
          "maximum" => @max_monthly_expenditure.round(2)
      },
      "username" => user.username
    }
  end

end