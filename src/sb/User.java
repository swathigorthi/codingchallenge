package sb;

public class User {
	private long userId;
	private String name;
	private Long groupId;
	
	public User(long userId, String name, Long groupId){
		this.userId = userId; 
		this.name = name;
		this.groupId = groupId;
	}

	public long getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public Long getGroupId() {
		return groupId;
	}
}
