class User < ActiveRecord::Base

  has_many :transactions, dependent: :destroy
  has_many :categories, dependent: :destroy

  after_create :create_user_categories

  def self.create_user_if_doesnt_exists_for username
    username_in_lowercase = username.downcase
    user = User.where(username: username_in_lowercase).first
    User.create!(:username => username_in_lowercase) unless user
    User.where(username: username_in_lowercase).first
  end

  private

  def create_user_categories
    Category::Type::ALL.each do |category_name|
      self.categories.create!(name: category_name)
    end
  end

end