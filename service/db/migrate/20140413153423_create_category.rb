class CreateCategory < ActiveRecord::Migration

  def up
    create_table :categories do |t|
      t.string :name
      t.belongs_to :user
      t.timestamps
    end
  end

  def down
    drop_table :categories
  end

end
