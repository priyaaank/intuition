class Transaction < ActiveRecord::Base

  belongs_to :user
  belongs_to :category

  scope :categorized, -> { joins(:category).group("categories.name") }
  scope :category_wise_sum, -> { categorized.sum(:price) }
  scope :monthly, -> { group("strftime('%m%Y', transaction_date)") }
  scope :monthly_sum, -> { monthly.sum(:price) }
  scope :past_months, ->(number_of_months) { where("transaction_date > ?", Date.today<<number_of_months) }
  scope :for_current_month, -> { where("transaction_date > ?", Date.new(Date.today.year, Date.today.month, 1)) }
  scope :for_year_and_month, ->(year, month) { where("transaction_date between ? and ? ", Date.new(year, month, 1)-1, Date.civil(year, month, -1)+1) }
  scope :until_year_and_month, ->(year, month) { where("transaction_date between ? and ? ", Date.new(oldest.transaction_date.year, oldest.transaction_date.month, 1)-1, Date.civil(year, month, -1)+1)}
  scope :oldest, ->{ order(:transaction_date => :asc).first }

end