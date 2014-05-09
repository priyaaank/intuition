class EveryFewMonthsExpenseDateGenerator

  attr_accessor :random_percent, :since_months, :generated_dates, :times_in_months, :month_count

  def initialize(exclusion_percent = 10, since_months = 20, month_count = 3, times_in_months = 1)
    @exclusion_percent = exclusion_percent
    @since_months = since_months
    @times_in_months = times_in_months
    @month_count = month_count
    @generated_dates = []
  end

  def generate_dates
    @generated_dates = all_dates_since_months(@since_months)
    remove_dates_based_on_exclusion_percentage
    @generated_dates
  end

  private

  def all_dates_since_months since_months
    exact_month_group_count = since_months/@month_count
    dates = (1..exact_month_group_count).to_a.collect do |month|
      past_date = Date.today<<(month*@month_count)
      start_date = Date.new(past_date.year, past_date.month, 1)
      future_date = (start_date >> @month_count)
      end_date = Date.civil(future_date.year, future_date.month, -1)
      end_date = Date.today if end_date > Date.today
      (start_date..end_date).to_a.sample(@times_in_months)
    end.flatten
    dates_of_current_month = dates.select { |d| d.month == Date.today.month && d.year == Date.today.year }
    dates_to_discard = dates_of_current_month.sample(dates_of_current_month.size/2)
    dates_to_discard.each {|d| dates.delete(d) }
    dates
  end

  def remove_dates_based_on_exclusion_percentage
    num_of_dates_to_exclude = ((@generated_dates.count * @exclusion_percent)/100)
    num_of_dates_to_exclude.times do |i|
      @generated_dates.delete_at(rand(1..@generated_dates.count))
    end
  end

end