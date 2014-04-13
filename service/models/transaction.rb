class Transaction < ActiveRecord::Base

  belongs_to :user
  belongs_to :category

  scope :categorized, -> { group(:category) }
  scope :category_wise_sum, -> { categorized.sum(:price) }
  scope :past_months, ->(number_of_months) { where("transaction_date > ?", Date.today<<number_of_months) }
  scope :for_current_month, -> { where("transaction_date > ?", Date.new(Date.today.year, Date.today.month, 1)) }

end