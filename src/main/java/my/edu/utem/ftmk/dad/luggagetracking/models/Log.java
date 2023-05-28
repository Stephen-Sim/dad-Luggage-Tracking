package my.edu.utem.ftmk.dad.luggagetracking.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "Logs")
public class Log {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "DateTime", nullable = false)
    private LocalDateTime dateTime;
	
	@ManyToOne
    @JoinColumn(name = "StatusId", nullable = false)
    private StatusType status;

    @ManyToOne
    @JoinColumn(name = "LuggageId", nullable = false)
    private Luggage luggage;

    @ManyToOne
    @JoinColumn(name = "CheckPointId", nullable = false)
    private CheckPoint checkPoint;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public Luggage getLuggage() {
		return luggage;
	}

	public void setLuggage(Luggage luggage) {
		this.luggage = luggage;
	}

	public CheckPoint getCheckPoint() {
		return checkPoint;
	}

	public void setCheckPoint(CheckPoint checkPoint) {
		this.checkPoint = checkPoint;
	}
}
