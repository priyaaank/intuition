require 'date'

class FakeExpense

  attr_accessor :expense_range, :merchant_listing_file, :frequency_in_year, :expense_type, :user, :category

  def initialize(expense_range, merchant_listing_file, frequency_in_year, expense_type, user, category)
    @merchant_listing_file = merchant_listing_file
    @frequency_in_year = frequency_in_year
    @expense_range = expense_range
    @expense_type = expense_type
    @dates_generator = ExpenseDateGenerator.new(Date.today, @frequency_in_year)
    @user = user
    @category = category

    extract_merchant_names
  end

  def create_expenses
    generate_dates = @dates_generator.generate_dates
    generate_dates.each do |date|
      random_merchant = random_merchant_name
      Transaction.create!(:merchant_name => random_merchant[0], :merchant_id => random_merchant[1], :transaction_date => date, :price => random_price_within_range, :user => user, :category => @category)
    end
  end

  def random_price_within_range
    (expense_range[0]..expense_range[1]).to_a.sample + ((0..99).to_a.sample / 100)
  end

  def random_merchant_name
    (@merchants ||= extract_merchant_names).sample
  end

  def extract_merchant_names
    merchant_details = []
    f = File.new(@merchant_listing_file, "r")
    while(name = f.gets)
      merchant_details << (name.chomp).split(",")
    end
    f.close
    merchant_details
  end

end
