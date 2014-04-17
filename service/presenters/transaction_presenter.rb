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

  def to_json
    puts "*"*100
    puts @transaction_date.strftime("%d/%m/%Y")
    puts "*"*100
    {
      :id => @id,
      :merchant_name => @merchant_name,
      :merchant_id => @merchant_id,
      :category_id => @category_id,
      :transaction_date => @transaction_date.strftime("%d/%m/%Y"),
      :price => @price
    }.to_json
  end


end