class CreateCatgories < ActiveRecord::Migration

  def up
    Category::Type::ALL.each do |category_name|
      Category.create!(name: category_name)
    end
  end

  def down
    Category.delete_all
  end
end
