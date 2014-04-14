class UserCategory

  def initialize(category)
    @name = category.name
    @id = category.id
  end

  def to_json
    {
        "id" => @id,
        "name" => @name
    }.to_json
  end

end