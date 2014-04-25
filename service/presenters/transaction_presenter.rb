class TransactionPresenter

  attr_accessor :transaction_date, :merchant_name, :merchant_id, :category_id, :id, :price

  def initialize(transaction)
    @id = transaction.id
    @merchant_name = transaction.merchant_name
    @merchant_id = transaction.merchant_id
    @category_id = transaction.category.id
    @transaction_date = transaction.transaction_date
    @price = transaction.price
  end

  def as_json(options={})
    {
      :id => @id,
      :merchant_name => @merchant_name,
      :merchant_id => @merchant_id,
      :category_id => @category_id,
      :transaction_date => @transaction_date.strftime("%Y-%m-%d"),
      :price => @price
    }
  end


end
