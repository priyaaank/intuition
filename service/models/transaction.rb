class Transaction < ActiveRecord::Base

  module Category
    FEES_AND_CHARGES = "FeesAndCharges"
    FOOD = "Food"
    HEALTH = "Health"
    INVESTMENTS = "Investments"
    SHOPPING = "Shopping"
    TRAVEL = "Travel"
    UTILITIES = "Utilities"
  end

  belongs_to :user

  scope :categorized, -> { group(:category) }

end