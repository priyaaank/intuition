require 'date'

class ExpenseDateGenerator

  attr_accessor :end_date, :dates_count

  def initialize(end_date, total_dates_count)
    @end_date = end_date
    @dates_count = total_dates_count
  end

  def generate_dates
    random_dates_across_last_one_year + random_dates_each_month_for_last_one_year
  end

  private

  def random_dates_across_last_one_year
    (1..dates_across_year).collect do |date_index|
      (@end_date<<12-date_index)
    end

  end

  def random_dates_each_month_for_last_one_year
    date_count = dates_per_month
    (1..12).to_a.collect do |month_index|
      (1..date_count).to_a.collect do |date_index|
        (@end_date<<month_index..@end_date<<month_index-1).to_a.sample
      end
    end.flatten
  end

  def dates_across_year
    @dates_count % 12
  end

  def dates_per_month
    @dates_count / 12
  end

end