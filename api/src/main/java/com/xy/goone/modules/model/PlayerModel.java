package com.xy.goone.modules.model;

import com.xy.goone.modules.domain.Player;
import com.xy.goone.modules.domain.Team;

/**
 * @author fumei.jiang
 * @date 2019-08-23 16:17
 */
public class PlayerModel {
    public static class PlayerInfo{
        Player player;

        public PlayerInfo(Player player) {
            this.player = player;
        }

        public int getId(){return player.getId();}
        public String getAvatar(){return player.getAvatar();}
        public String getName(){return player.getName();}
        public Integer getFansQuantity(){return player.getFansQuantity();}
        public Integer getGiftQuantity(){return player.getGiftQuantity();}
        public String getTeamName(){return player.getTeam().getName();}
        public Integer getCoin(){return player.getCoin();}
        private int status;

        public int getStatus() { return status; }

        public void setStatus(int status) { this.status = status;}
    }

    public static class PlayerFocus extends PlayerInfo{
        private Team team;
        public PlayerFocus(Player player) {
            super(player);
        }

        public Team getTeam() {
            return team;
        }

        public void setTeam(Team team) {
            this.team = team;
        }
    }

    public static class PlayerQuantity{
        Player player;

        public PlayerQuantity(Player player) {
            this.player = player;
        }

        public int getId(){return player.getId();}

        public Integer getGiftQuantity(){return player.getGiftQuantity();}
    }



}
