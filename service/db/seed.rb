class MonthlyExpense

  attr_accessor :expense_range, :merchant_listing_file, :merchants, :frequency_per_month

  def random_merchant_name
    (@merchants ||= extract_merchant_names)[]
  end

  private

  def extract_merchant_names
    merchant_names = []
    f = File.new(@merchant_listing_file, "r")
    while(name = f.gets)
      merchant_names << name
    end
    f.close
    merchant_names
  end

end

class UtilityExpense < MonthlyExpense

  def initialize
    @merchant_listing_file = ""
    @frequency = MonthlyExpense::FREQUENCY::MONTHLY
  end

end



Transaction.create!(:merchant_name => "KFC", :merchant_id => "0ebcd3f2-ea47-4247-98c1-e4d241f7d412", :transaction_date => DateTime.now, :price => 12.45)