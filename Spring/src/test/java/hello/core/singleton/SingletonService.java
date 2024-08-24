package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() { // 딱 1개의 객체 인스턴스만 존재해야 하므로 생성자를 private 로 막아서 호출되는 것을 막는다

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
