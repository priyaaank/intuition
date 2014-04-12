class CreateTransactions < ActiveRecord::Migration

  def up
    create_table :transactions do |t|
      t.string :merchant_name
      t.integer :merchant_id
      t.datetime :transaction_date
      t.string :category
      t.float :price
      t.belongs_to :user
      t.timestamps
    end
  end

  def down
    drop_table :transactions
  end

end