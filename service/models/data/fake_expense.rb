require 'date'

class FakeExpense

  attr_accessor :expense_range, :merchant_listing_file, :frequency_in_year, :expense_type, :user

  def initialize(expense_range, merchant_listing_file, frequency_in_year, expense_type, user)
    @merchant_listing_file = merchant_listing_file
    @frequency_in_year = frequency_in_year
    @expense_range = expense_range
    @expense_type = expense_type
    @dates_generator = ExpenseDateGenerator.new(Date.today, @frequency_in_year)
    @user = user

    extract_merchant_names
  end

  def create_expenses
    generate_dates = @dates_generator.generate_dates
    generate_dates.each do |date|
      Transaction.create!(:merchant_name => random_merchant_name, :transaction_date => date, :price => random_price_within_range, :user => user)
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
      merchant_names << name.chomp
    end
    f.close
    merchant_names
  end

end