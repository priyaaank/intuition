class User < ActiveRecord::Base

  def self.create_user_if_doesnt_exists_for username
    username_in_lowercase = username.downcase
    user = User.where(username: username_in_lowercase).first
    User.create!(:username => username_in_lowercase) unless user
  end

end