package sb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Processor extends Thread {
	private final Map<Long, Set<Long>> groupToUserMap = new HashMap<>();
	private final List<User> userList = new ArrayList<>();
	private boolean inputProcessingDone = false;
	
    
	public void addGroup(String line){
		String[] arr = line.split(":");
		Long groupId = Long.valueOf(arr[0]);
		String[] userIds = arr.length < 3 ? null : arr[2].split(",");
		Set<Long> userIdSet = userIds !=  null //
				? new HashSet<>(Arrays.stream(userIds).mapToLong(x -> Long.valueOf(x)).boxed().collect(Collectors.toSet())) 
				: new HashSet<>();
		groupToUserMap.put(groupId, userIdSet);
	}
    
    public void addUser(String line){
    	String[] arr = line.split(":");
		Long userId = Long.valueOf(arr[0]);
		String name = arr[1];
		Long groupId = arr.length < 3 ? null : Long.valueOf(arr[2]);
		
    	synchronized(userList){
    		userList.add(new User(userId, name, groupId));
    		userList.notify();
    	}
    }

    public void processingDone(){
    	inputProcessingDone = true;
    	synchronized(userList){
    		userList.notify();
    	}
    }
    
    public void run(){
    	while(true){
    	    synchronized(userList){
    	    	while(userList.isEmpty() && !inputProcessingDone){
    	    		try {
    	    			userList.wait();
					} catch (InterruptedException e) {}
    	    	}
	    		Iterator<User> iter = userList.iterator();
	    		while (iter.hasNext()) {
	    		    User entry = iter.next();
	    		    if(userHasNoValidGroup(entry.getUserId(), entry.getGroupId())){
	    		    	System.out.println("Id: " +entry.getUserId()+ ", Name: " + entry.getName());
	    		    }
	    		    iter.remove();
	    		}
    	    	
    	    }
    	    if(inputProcessingDone){
    	    	break;
    	    }
    	}    	
    }
    
    private  boolean userHasNoValidGroup(Long userId, Long groupId) {
		return groupId == null || !groupToUserMap.containsKey(groupId) || !groupToUserMap.get(groupId).contains(userId);
	}
}
