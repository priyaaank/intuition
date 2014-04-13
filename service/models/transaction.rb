class Transaction < ActiveRecord::Base

  module Category
    FEES_AND_CHARGES = "FeesAndCharges"
    FOOD = "Food"
    HEALTH = "Health"
    INVESTMENTS = "Investments"
    SHOPPING = "Shopping"
    TRAVEL = "Travel"
    UTILITIES = "Utilities"
    ALL = [FEES_AND_CHARGES, FOOD, HEALTH, INVESTMENTS, SHOPPING, TRAVEL, UTILITIES]
  end

  belongs_to :user

  scope :categorized, -> { group(:category) }
  scope :category_wise_sum, -> { categorized.sum(:price) }
  scope :past_months, ->(number_of_months) { where("transaction_date > ?", Date.today<<number_of_months) }
  scope :for_current_month, -> { where("transaction_date > ?", Date.new(Date.today.year, Date.today.month, 1)) }

end