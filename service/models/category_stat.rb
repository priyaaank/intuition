class CategoryStat

  attr_accessor :category_name, :total_amount_spent, :total_number_of_transactions, :expected_expense

  def initialize(category_id, category_name, total_amount_spent, total_number_of_transactions)
    @category_id = category_id
    @category_name = category_name
    @total_amount_spent = total_amount_spent
    @total_number_of_transactions = total_number_of_transactions
    @expected_expense = 0
  end

  def self.category_stats_from transactions
    grouped_transactions = transactions.group_by { |t| t.category_id }
    grouped_transactions.keys.collect do |category_id|
      name = Category.find_by_id(category_id).name
      total_num_of_transactions =  grouped_transactions[category_id].count
      total_spent_amount = Array.wrap(grouped_transactions[category_id]).map(&:price).sum
      CategoryStat.new(category_id, name, total_spent_amount, total_num_of_transactions)
    end
  end

end