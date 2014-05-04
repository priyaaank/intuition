class CreateBudget < ActiveRecord::Migration

  def up
    create_table :budgets do |t|
      t.float :amount
      t.integer :month
      t.integer :year
      t.belongs_to :user
      t.timestamps
    end
  end

  def down
    drop_table :budget
  end

end
