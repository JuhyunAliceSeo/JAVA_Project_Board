import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BoardDAO { //게시글 객체
	Scanner scanner = new Scanner(System.in); 
	List<BoardVO> boardList = new ArrayList<>(); //게시글 객체를 저장할 List
	
	public void getBoardList() { //게시판 목록 불러오기
		System.out.println("        		<<게시판>>");
		System.out.println("번호    	  제목    		            작성자    		    작성일    ");
		System.out.println("-----------------------------------------------------------");
		if (boardList.isEmpty()) { //게시글 객체들을 담은 리스트에 아무것도 없는 경우
			System.out.println("                	 게시글 없음");
		} else {
			for (BoardVO vo : boardList) { //글번호를 1부터 시작하기 위해 인덱스 +1
				System.out.printf("%-3d    %-15s    %3s    %-1s\n", boardList.indexOf(vo) + 1, vo.getTitle(),
						vo.getWriter(), vo.getRegistDate());
			}
		}
		System.out.println("-----------------------------------------------------------");
		System.out.println("(1.새로쓰기, 2.상세보기, 3.수정, 4.삭제, 5.파일, 6.종료) : ");
	}
		
	public void boardInsert() { //게시글 새로쓰기
		BoardVO vo = new BoardVO(); //게시글 객체생성
		
		System.out.print("글제목(취소:quit): "); //제목 입력
		String title = scanner.nextLine();
		if (title.equals("quit")) {
			System.out.println("작성이 취소되었습니다.");
			return;
		}
		
		System.out.print("작성자(취소:quit): "); //작성자 입력
		String writer = scanner.nextLine();
		if (writer.equals("quit")) {
			System.out.println("작성이 취소되었습니다.");
			return;
		}
		
		System.out.print("글내용(취소:quit): ");//내용 입력
		String content = scanner.nextLine();
		if (content.equals("quit")) {
			System.out.println("작성이 취소되었습니다.");
			return;
		}
		
		vo.setNum(boardList.size() + 1); //인덱스를 객체번호에 저장
		vo.setTitle(title); // 글제목 저장
		vo.setWriter(writer); //글 작성자 저장
		vo.setContent(content); // 글 내용 저장
		
		//현재 날짜 저장
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String registDate = sdf.format(new Date());
		vo.setRegistDate(registDate);
		
		boardList.add(vo); //List에 혀재 작성한 게시글 저장
		System.out.println("글이 추가되었습니다.\n");
	}

	public void boardDetail(int select) { //게시글 상세보기
		if (boardList.isEmpty()) { //boardList가 비어있다면
			System.out.println("게시글이 존재하지 않습니다.");
			return;
		}
		
		BoardVO vo = boardList.get(select - 1); //게시글 객체 생성
		vo=boardList.get(select - 1);//선택한 글의 상세페이지 출력
		System.out.println("NO. " + select); //글번호가 1부터시작하니까 +1
		System.out.println("제목 : " + vo.getTitle());
		System.out.println("작성자: " + vo.getWriter());
		System.out.println("----------------------------------------------------------");
		System.out.println(vo.getContent());
		System.out.println("----------------------------------------------------------");
		System.out.println("(1. 수정, 2. 삭제, 3.목록) : ");
		select = Integer.parseInt(scanner.nextLine());
		
		if (select == 1) { //현재 글수정
			boardUpdate(select);
		} else if (select == 2) { //현재 글 삭제
			boardDelete(select);
		} else if (select == 3) { //목록으로 이동
			return;
		}
	}
	
	public void boardUpdate(int select) { //게시글 수정
		if (boardList.isEmpty()) { //boardList가 비어있는 경우
			System.out.println("게시글이 없습니다");
			return;
		}
		
		BoardVO vo = boardList.get(select - 1); //게시글 객체 생성
		
		System.out.print("글제목(취소 : quit): "); //제목수정
		String title = scanner.nextLine();
		if (title.equals("quit")) {
			System.out.println("수정이 취소되었습니다");
			return;
		}
		
		System.out.print("작성자(취소 : quit): "); //작성자 수정
		String writer = scanner.nextLine();
		if (writer.equals("quit")) {
			System.out.println("수정이 취소되었습니다");
			return;
		}
		
		System.out.print("글내용(취소 : quit): "); //내용수정
		String content = scanner.nextLine();
		if (content.equals("quit")) {
			System.out.println("수정이 취소되었습니다");
			return;
		}
		
		//수정 취소를 하지 않았을 경우 입력한 값을 저장
		vo.setTitle(title);
		vo.setWriter(writer);
		vo.setContent(content);
		
		//등록한 현재날짜 저장
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd (수정됨)");
		String registDate = sdf.format(new Date());
		vo.setRegistDate(registDate); //등록날짜를 수정날짜로 변경
		
		System.out.println("글이 수정되었습니다.\n");
	}
	
	public void boardDelete(int select) { //게시글 삭제
		if (boardList.isEmpty()) {
			System.out.println("게시글이 없습니다.");
			return;
		}
		
		BoardVO vo = boardList.get(select - 1); // 선택한 번호의 객체
		boardList.remove(vo);//해당인덱스에 있는 객체 삭제
		System.out.println(select + "번 글이 삭제되었습니다.");
	}
	
	public void txtRead() throws Exception { //입력 스트림(메모장 파일 읽기)
		Reader reader = new FileReader("C:/Temp/BoardDB.txt"); //해당 경로를 참조하는 입력스트림 객체 생성
		int data;
		while ((data = reader.read()) != -1) { //데이터를 한문자씩 읽어와 숫자로 저장됨 / 없는 경우
			System.out.print((char) data); //받아온 데이터를 문자로 변환하여 출력
		}
		reader.close();
		System.out.println("\n데이터 로드 완료");
	}
	
	public void txtWrite() throws Exception { // 출력스트림(메모장 파일쓰기)
		Writer writer = new FileWriter("C:/Temp/BoardDB.txt"); //해당경로를 참조하는 출력 스트림 객체 생성
		for (BoardVO vo : boardList) { //boardList의 객체를 하나씩 불러 data에 해당값들을 저장
			String data = vo.getNum() + "|\t" + vo.getTitle() + "|\t" + vo.getContent() +
			"|\t" + vo.getWriter() + "|\t" + vo.getRegistDate() + "|\t";
			writer.write(data);
		}
		
		writer.flush(); //버퍼에 남은 데이터를 파일로 출력후 버퍼를 이용 
		                // 버퍼가 꽉 차기 전에 프로그램이 종료되면 버퍼에 남아있는 내용이 파일로 출력이 되지 않기 때문에 사용(사용자가 원할때 사용하면 됨)
		
		writer.close(); //출력 스트림 닫기
		System.out.println("현재 데이터 저장 완료");
	}
}