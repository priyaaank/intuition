class CreateBudget < ActiveRecord::Migration

  def up
    create_table :budget do |t|
      t.string :name
      t.belongs_to :category
      t.belongs_to :user
      t.timestamps
    end
  end

  def down
    drop_table :budget
  end

end
