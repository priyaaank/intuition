class Category < ActiveRecord::Base

  module Type
    FEES_AND_CHARGES = "FeesAndCharges"
    FOOD = "Food"
    HEALTH = "Health"
    INVESTMENTS = "Investments"
    SHOPPING = "Shopping"
    TRAVEL = "Travel"
    UTILITIES = "Utilities"
    UNKNOWN = "Unknown"
    ALL = [FEES_AND_CHARGES, FOOD, HEALTH, INVESTMENTS, SHOPPING, TRAVEL, UTILITIES, UNKNOWN]
  end

  has_many :transactions, :dependent => :nullify
  belongs_to :user

end
