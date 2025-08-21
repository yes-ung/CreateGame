cc.Class({
    extends: cc.Component,

    properties: {
        stageLabel: cc.Label, // 에디터에서 Label 노드 연결
    },

    start () {
        fetch('http://localhost:8080/api/stages') // ← Spring Boot API 주소
            .then(response => response.json())
            .then(data => {
                if (data && data.length > 0) {
                    const firstStage = data[0];
                    this.stageLabel.string = `스테이지: ${firstStage.name} / 난이도: ${firstStage.difficulty}`;
                } else {
                    this.stageLabel.string = '스테이지 없음';
                }
            })
            .catch(error => {
                console.error('API 호출 실패:', error);
                this.stageLabel.string = '데이터 불러오기 실패';
            });
    },
});
