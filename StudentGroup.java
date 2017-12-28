import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class StudentGroup implements GroupOperationService {

	private Student[] students;
	
	public StudentGroup(int length) {
		this.students = new Student[length];
	}

	@Override
	public Student[] getStudents() {
		return this.students;
	}

	@Override
	public void setStudents(Student[] students) {
		if(students == null) throw new IllegalArgumentException();
		this.students = students;
	}
	
	@Override
	public Student getStudent(int index) {
		if(index<0 || index>students.length) throw new IllegalArgumentException();
		return students[index];
	}

	@Override
	public void setStudent(Student student, int index) {
		if(student == null) throw new IllegalArgumentException();
		if(index<0 || index>students.length) throw new IllegalArgumentException();
		this.students[index] = student;
	}

	@Override
	public void addFirst(Student student) {
		if(student == null) throw new IllegalArgumentException();
		Student copyS[] = students;
		students = new Student[copyS.length + 1];
		for (int i = 0, j = 1; i < copyS.length; i++, j++)
			students[j] = copyS[i];
		students[0] = student;
	}

	@Override
	public void addLast(Student student) {
		if(student == null) throw new IllegalArgumentException();
		Student copyS[] = students;
		students = new Student[copyS.length + 1];
		for (int i = 0; i < copyS.length; i++)
			students[i] = copyS[i];
		students[copyS.length] = student;
		}

	@Override
	public void add(Student student, int index) {
		if(student==null)throw new IllegalArgumentException();
		if(index<0 || index>students.length) throw new IllegalArgumentException();
		Student copyS[] = students;
		students = new Student[copyS.length + 1];
		for(int i=0; i<copyS.length && i<index; i++)
			students[i]=copyS[i];
		students[index]=student;
		for (int i = index+1; i < copyS.length; i++)
			students[i] = copyS[i];
	}

	@Override
	public void remove(int index) {
		if(index<0 || index>students.length) throw new IllegalArgumentException();
		for(int i=index; i<students.length-1; i++)
			students[i]=students[i+1];
		students[students.length-1]=null;
	}

	@Override
	public void remove(Student student) { 
		if(student==null)throw new IllegalArgumentException();
		for(int i=0; i<students.length; i++)
			if((student.getId()== students[i].getId()) && (student.getFullName().compareTo(students[i].getFullName())==0) && (student.getBirthDate() == students[i].getBirthDate()) && (student.getAvgMark() == students[i].getAvgMark())){
				this.remove(i);
			}
				
	}
	@Override
	public void removeFromIndex(int index) {
		if(index<0 || index>students.length) throw new IllegalArgumentException();
		for(int i=index+1; i<students.length; i++)
			students[i]= null;
	}

	@Override
	public void removeFromElement(Student student) {
		if(student==null) throw new IllegalArgumentException();
		int index =0;
		for(int i=0; i<students.length; i++)
			if((student.getId()== students[i].getId()) && (student.getFullName().compareTo(students[i].getFullName())==0) && (student.getBirthDate() == students[i].getBirthDate()) && (student.getAvgMark() == students[i].getAvgMark())){
				index = i;
				break;
			}
		this.removeFromIndex(index);
		
	}

	@Override
	public void removeToIndex(int index) {
		if(index<0 || index>students.length) throw new IllegalArgumentException();
		for(int i=0; i<students.length; i++)
			if(i<index)
				students[i]=null;
	}

	@Override
	public void removeToElement(Student student) {
		if(student==null) throw new IllegalArgumentException();
		int index =0;
		for(int i=0; i<students.length; i++)
			if((student.getId()== students[i].getId()) && (student.getFullName().compareTo(students[i].getFullName())==0) && (student.getBirthDate() == students[i].getBirthDate()) && (student.getAvgMark() == students[i].getAvgMark())){
				index = i;
				break;
			}
		this.removeToIndex(index);
	}

	@Override
	public void bubbleSort() {
		for (int i = students.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (students[j].getFullName().compareTo(students[j+1].getFullName()) >0) {
                    Student student = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = student;
                }
            }
        }
	}

	@Override
	public Student[] getByBirthDate(Date date) {
		if(date==null) throw new IllegalArgumentException();
		int counter=0, j=0;
		for(int i=0; i<students.length; i++)
			if(students[i].getBirthDate().compareTo(date) == 0)
				counter++;
		Student studentDate[] = new Student[counter];
		for(int i=0; i<students.length; i++)
			if(students[i].getBirthDate().compareTo(date) == 0){
				studentDate[j] = students[i];
				j++;
			}
				
		return studentDate;
	}

	@Override
	public Student[] getBetweenBirthDates(Date firstDate, Date lastDate) {
		if(firstDate==null || lastDate==null)throw new IllegalArgumentException();
		int counter=0, j=0;
		for(int i=0; i<students.length; i++)
			if(students[i].getBirthDate().after(firstDate) == true && students[i].getBirthDate().before(lastDate) == true)
				counter++;
		Student studentDate[] = new Student[counter];
		for(int i=0; i<students.length; i++)
			if(students[i].getBirthDate().after(firstDate) == true && students[i].getBirthDate().before(lastDate) == true){
				studentDate[j] = students[i];
				j++;
			}
		return studentDate;
	}

	@Override
	public Student[] getNearBirthDate(Date date, int days) {
		if(date==null)throw new IllegalArgumentException();
		Calendar calendar1 = dateToCalendar(date);
		Calendar calendar2 = calendar1;
		calendar1.add(Calendar.DAY_OF_MONTH, days);
		calendar2.add(Calendar.DAY_OF_MONTH, days);
		Date date1 = calendarToDate(calendar1);
		Date date2 = calendarToDate(calendar2);
		return this.getBetweenBirthDates(date2, date1);
	}
	private Date calendarToDate(Calendar calendar) {
		return calendar.getTime();
	}

	@Override
	public int getCurrentAgeByDate(int indexOfStudent) {
		if(indexOfStudent==0)throw new IllegalArgumentException();
		Calendar calendar = dateToCalendar(students[indexOfStudent].getBirthDate());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int age = 2017 - year;
		if(month> 12)
			age--;
		else if(month ==12)
			if(day<=26)
				age--;
		return age;
	}
	private Calendar dateToCalendar(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	@Override
	public Student[] getStudentsByAge(int age) {
		int counter=0;
		for(int i=0; i<students.length; i++)
			if(this.getCurrentAgeByDate(i)==age)
				counter++;
		Student studentAge[] = new Student[counter];
		for(int i=0; i<students.length; i++)
			if(this.getCurrentAgeByDate(i)==age)
				studentAge[i]=students[i];		
		return studentAge;
	}

	@Override
	public Student[] getStudentsWithMaxAvgMark() {
		double max = students[0].getAvgMark();
		int counter=0;
		for(int i=1; i<students.length; i++)
			if(students[i].getAvgMark()> max)
				max = students[i].getAvgMark();
		for(int i=0; i<students.length; i++)
			if(students[i].getAvgMark() == max)
				counter++;
		Student studentMaxAvg[] = new Student[counter];
		
		return studentMaxAvg;
	}

	@Override
	public Student getNextStudent(Student student) {
		if(student==null)throw new IllegalArgumentException();
		int index = 0;
		for(int i=0; i<students.length; i++)
			if((student.getId()== students[i].getId()) && (student.getFullName().compareTo(students[i].getFullName())==0) && (student.getBirthDate() == students[i].getBirthDate()) && (student.getAvgMark() == students[i].getAvgMark())){
				index = i+1;
			}
		
		return students[index];
	}
}
