class MonthlyExpenseDateGenerator

  attr_accessor :random_percent, :since_months, :generated_dates, :times_a_month

  def initialize(exclusion_percent = 10, since_months = 20, times_a_month = 1)
    @exclusion_percent = exclusion_percent
    @since_months = since_months
    @times_a_month = times_a_month
    @generated_dates = []
  end

  def generate_dates
    @generated_dates = all_days_since_months(@since_months)
    remove_dates_based_on_exclusion_percentage
    @generated_dates
  end

  private

  def all_days_since_months month_count
    month_count.times.collect do |month|
      dates = []
      past_date = Date.today<<month
      start_date = Date.new(past_date.year, past_date.month, 1)
      end_date = Date.civil(past_date.year, past_date.month, -1)
      if end_date > Date.today
        end_date = Date.today<<1
        dates += (start_date..end_date).to_a.sample(@times_a_month)
        dates += (Date.new(Date.today.year,Date.today.month,1)..Date.today).to_a.sample(@times_a_month/2)
      else
        dates += (start_date..end_date).to_a.sample(@times_a_month)
      end
      dates
    end.flatten
  end

  def remove_dates_based_on_exclusion_percentage
    num_of_dates_to_exclude = ((@generated_dates.count * @exclusion_percent)/100)
    num_of_dates_to_exclude.times do |i|
      @generated_dates.delete_at(rand(1..@generated_dates.count))
    end
  end

end