package com.main.virtualvisitor88;
import com.main.virtualvisitor88.walkCallback;

interface activityCallback{
	void setObserver(walkCallback observer);
	void removeObserver(walkCallback observer);
}