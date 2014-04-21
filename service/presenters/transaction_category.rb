class TransactionCategory

  attr_accessor :name, :count, :price_total

  def initialize(name, count, price_total)
    @name = name
    @count = count||0
    @price_total = price_total||0
  end

  def as_json(options = {})
    {
        "name" => @name,
        "price_sum" => @price_total,
        "total" => @count
    }
  end

end