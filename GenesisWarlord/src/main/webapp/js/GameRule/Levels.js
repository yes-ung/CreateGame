var Levels=[
    {
        level:1,
        load:function(){
            //Load map
            Map.setCurrentMap('TrenchWars');
            //Apply race style
            Game.race.choose('Terran');
            //Load units
            new Terran.Marine({x:100,y:100});
            new Terran.Marine({x:100,y:100});
            new Terran.Marine({x:100,y:100});
            new Terran.Marine({x:100,y:100});
            new Terran.Marine({x:100,y:100});
            new Terran.Marine({x:100,y:100});
            new Terran.Marine({x:100,y:100});
            new Terran.Marine({x:100,y:100});
            new Zerg.Zergling({x:400,y:100,team:1});
            new Zerg.Zergling({x:400,y:100,team:1});
            new Zerg.Zergling({x:400,y:100,team:1});
            new Zerg.Zergling({x:400,y:100,team:1});
            new Zerg.Zergling({x:400,y:100,team:1});
            new Zerg.Zergling({x:400,y:100,team:1});
            new Zerg.Zergling({x:400,y:100,team:1});
            new Zerg.Zergling({x:400,y:100,team:1});
            new Zerg.Zergling({x:400,y:100,team:1});
            new Zerg.Zergling({x:400,y:100,team:1});
            new Zerg.Zergling({x:500,y:100,team:1});
            new Zerg.Zergling({x:500,y:100,team:1});
            new Zerg.Zergling({x:500,y:100,team:1});
            new Zerg.Zergling({x:500,y:100,team:1});
            new Zerg.Zergling({x:500,y:100,team:1});
            new Zerg.Zergling({x:500,y:100,team:1});




        }
    },
     {
         level:2,
         load:function() {
             //Load map
             Map.setCurrentMap('TrenchWars');
             //Apply race style
             Game.race.choose('Terran');
             //Load units
             new Terran.Marine({x: 100, y: 100});
             new Terran.Marine({x: 100, y: 100});
             new Terran.Marine({x: 100, y: 100});
             new Terran.Marine({x: 100, y: 100});
             new Terran.Marine({x: 100, y: 100});
             new Terran.Marine({x: 100, y: 100});
             new Terran.Marine({x: 100, y: 100});
             new Terran.Marine({x: 100, y: 100});

             new Terran.Marine({x: 400, y: 100, team: 1});
             new Terran.Marine({x: 400, y: 100, team: 1});
             new Terran.Marine({x: 400, y: 100, team: 1});
             new Terran.Marine({x: 400, y: 100, team: 1});
             new Terran.Marine({x: 400, y: 100, team: 1});
             new Terran.Marine({x: 400, y: 100, team: 1});
             new Terran.Marine({x: 400, y: 100, team: 1});
             new Terran.Marine({x: 400, y: 100, team: 1});
         }
    },
    {
        level:3,
        load:function(){
            //Load map
            Map.setCurrentMap('TrenchWars');
            //Apply race style
            Game.race.choose('Terran');
            //Load units
            new Terran.Marine({x:100,y:100});
            new Terran.Marine({x:200,y:100});
            new Zerg.Zergling({x:600,y:400,team:1});
            new Zerg.Zergling({x:700,y:100,team:1});
        }
    },
    {
        level:4,
        load:function(){
            //Load map
            Map.setCurrentMap('TrenchWars');
            //Apply race style
            Game.race.choose('Zerg');
            //Load units
            new Terran.Marine({x:100,y:100});
            new Terran.Marine({x:200,y:100});
            new Zerg.Zergling({x:600,y:400,team:1});
            new Zerg.Zergling({x:700,y:100,team:1});
        }
    },
    {
        level:5,
        load:function(){
            //Load map
            Map.setCurrentMap('TrenchWars');
            //Apply race style
            Game.race.choose('Terran');
            //Load units
            new Terran.Marine({x:100,y:100});
            new Terran.Marine({x:200,y:100});
            new Zerg.Zergling({x:600,y:400,team:1});
            new Zerg.Zergling({x:700,y:100,team:1});
        }
    },
    {
        level:6,
        load:function(){
            //Load map
            Map.setCurrentMap('TrenchWars');
            //Apply race style
            Game.race.choose('Zerg');
            //Load units
            new Terran.Marine({x:100,y:100});
            new Terran.Marine({x:200,y:100});
            new Zerg.Zergling({x:600,y:400,team:1});
            new Zerg.Zergling({x:700,y:100,team:1});
        }
    }
    
];