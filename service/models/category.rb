class Category < ActiveRecord::Base

  module Type
    Household = "Household"
    FuelAndTransport = "Fuel n Transport"
    Shopping = "Shopping"
    Medical = "Medical n Insurance"
    Recreation = "Recreation"
    FoodAndDrinks = "Food n Drinks"
    DiningOut = "Dining"
    Holiday = "Holiday"
    Investment = "Investment"
    FeesAndCharges = "Fees n Charges"
    Mortgage = "Mortgage"
    Uncategorized = "Uncategorized"
    ALL = [Household, FuelAndTransport, Shopping, Medical, Recreation, FoodAndDrinks, DiningOut, Holiday, Investment, FeesAndCharges, Mortgage, Uncategorized]
  end

  has_many :transactions, :dependent => :restrict_with_exception
  belongs_to :user

end
