class UserCategory

  def initialize(category)
    @name = category.name
    @id = category.id
  end

  def as_json(options={})
    {
        "id" => @id,
        "name" => @name
    }
  end

end