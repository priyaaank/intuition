require 'date'
require './models/transaction'

class Expense

  attr_accessor :expense_range, :merchant_listing_file, :frequency_in_year, :expense_type

  def initialize(expense_range, merchant_listing_file, frequency_in_year, expense_type)
    @merchant_listing_file = merchant_listing_file
    @frequency_in_year = frequency_in_year
    @expense_range = expense_range
    @expense_type = expense_type

    extract_merchant_names
  end

  def create_expenses
    random_transaction_dates.each do |date|
      Transaction.create!(:merchant_name => random_merchant_name, :transaction_date => date, :price => random_price_within_range)
    end
  end

  def random_price_within_range
    (expense_range[0]..expense_range[1]).to_a.sample + ((0..99).to_a.sample / 100)
  end

  def random_merchant_name
    (@merchants ||= extract_merchant_names).sample
  end

  def extract_merchant_names
    merchant_names = []
    f = File.new(@merchant_listing_file, "r")
    while(name = f.gets)
      merchant_names << name
    end
    f.close
    merchant_names
  end

  def random_transaction_dates
    (1..12).to_a.collect do |month_index|
      expense_count = number_of_expenses_per_month
      (1..expense_count).to_a.collect do |expense_index|
        (Date.today<<month_index..Date.today<<month_index-1).to_a.sample
      end
    end.flatten
  end

  def number_of_expenses_per_month
    @frequency_in_year / 12
  end

  def number_of_remaining_expenses_to_adjust
    @frequency_in_year % 12
  end

  def number_of_days_in_month(year, month)
    (new Date(year, 12, 31) << 12-month).day
  end

end


Expense.new([30, 150], "./data/samples/utilities.txt", 24, "House utilities expenses").create_expenses
Expense.new([5, 15], "./data/samples/food.txt", 950, "Daily food expenses").create_expenses
Expense.new([1, 5], "./data/samples/feesAndCharges.txt", 20, "Fees and charges expenses").create_expenses
