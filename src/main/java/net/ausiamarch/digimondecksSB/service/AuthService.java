/*
 * Copyright (c) 2021
 *
 * by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com) & 2021 DAW students
 *
 * WILDCART: Free Open Source Shopping Site
 *
 * Sources at:                https://github.com/rafaelaznar/wildCartSBServer2021
 * Database at:               https://github.com/rafaelaznar/wildCartSBServer2021
 * POSTMAN API at:            https://github.com/rafaelaznar/wildCartSBServer2021
 * Client at:                 https://github.com/rafaelaznar/wildCartAngularClient2021
 *
 * WILDCART is distributed under the MIT License (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.ausiamarch.digimondecksSB.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.ausiamarch.digimondecksSB.bean.PlayerBean;
import net.ausiamarch.digimondecksSB.exception.UnauthorizedException;
import net.ausiamarch.digimondecksSB.entity.PlayerEntity;
import net.ausiamarch.digimondecksSB.helper.JwtHelper;
import net.ausiamarch.digimondecksSB.helper.UsertypeHelper;
import net.ausiamarch.digimondecksSB.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    @Autowired
    private HttpServletRequest oRequest;

    @Autowired
    PlayerRepository oPlayerRepository;

    @Autowired
    HttpSession oHttpSession;

    public String login(@RequestBody PlayerBean oPlayerBean) {
        if (oPlayerBean.getPassword() != null) {
            PlayerEntity oPlayerEntity = oPlayerRepository.findByEmailAndPassword(oPlayerBean.getEmail(), oPlayerBean.getPassword());
            if (oPlayerEntity != null) {
                System.out.println(oPlayerEntity);
                return JwtHelper.generateJWT(oPlayerBean.getEmail(), oPlayerEntity.getUsertype().getId());
            } else {
                throw new UnauthorizedException("email or password incorrect");
            }
        } else {
            throw new UnauthorizedException("wrong password");
        }
    }

    public PlayerEntity check() {
        String strPlayer = (String) oRequest.getAttribute("player");
        if (strPlayer != null) {
            PlayerEntity oPlayerEntity = oPlayerRepository.findByEmail(strPlayer);
            return oPlayerEntity;
        } else {
            throw new UnauthorizedException("No active session");
        }
    }

    public boolean isAdmin() {
        PlayerEntity oPlayerSessionEntity = oPlayerRepository.findByEmail((String) oRequest.getAttribute("player"));
        if (oPlayerSessionEntity != null) {
            if (oPlayerSessionEntity.getUsertype().getId().equals(UsertypeHelper.ADMIN)) {
                return true;
            }
        }
        return false;
    }

    public void OnlyAdmins() {
        PlayerEntity oPlayerSessionEntity = oPlayerRepository.findByEmail((String) oRequest.getAttribute("player"));
        if (oPlayerSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to admin role");
        } else {
            if (!oPlayerSessionEntity.getUsertype().getId().equals(UsertypeHelper.ADMIN)) {
                throw new UnauthorizedException("this request is only allowed to admin role");
            }
        }
    }

    public boolean isLoggedIn() {
        String strPlayer = (String) oRequest.getAttribute("player");
        if (strPlayer == null) {
            return false;
        } else {
            return true;
        }
    }

    public Long getUserID() {
        String strPlayer = (String) oRequest.getAttribute("player");
        PlayerEntity oPlayerEntity = oPlayerRepository.findByEmail(strPlayer);
        if (oPlayerEntity != null) {
            return oPlayerEntity.getId();
        } else {
            throw new UnauthorizedException("this request is only allowed to auth Players");
        }
    }


    public boolean isPlayer() {
        String strPlayer = (String) oRequest.getAttribute("player");
        PlayerEntity oPlayerEntity = oPlayerRepository.findByEmail(strPlayer);
        if (oPlayerEntity != null) {
            if (oPlayerEntity.getUsertype().getId().equals(UsertypeHelper.PLAYER)) {
                return true;
            }
        }
        return false;
    }

    public void OnlyPlayers() {
        String strPlayer = (String) oRequest.getAttribute("player");
        PlayerEntity oPlayerEntity = oPlayerRepository.findByEmail(strPlayer);
        if (oPlayerEntity == null) {
            throw new UnauthorizedException("this request is only allowed to Player role");
        } else {
            if (!oPlayerEntity.getUsertype().getId().equals(UsertypeHelper.PLAYER)) {
                throw new UnauthorizedException("this request is only allowed to Player role");
            }
        }
    }

    public void OnlyAdminsOrPlayers() {
        String strPlayer = (String) oRequest.getAttribute("player");
        PlayerEntity oPlayerEntity = oPlayerRepository.findByEmail(strPlayer);
        if (oPlayerEntity == null) {
            throw new UnauthorizedException("this request is only allowed to admin or reviewer role");
        } else {
            if (oPlayerEntity.getUsertype().getId().equals(UsertypeHelper.ADMIN)) {
            } else {
                if (oPlayerEntity.getUsertype().getId().equals(UsertypeHelper.PLAYER)) {
                } else {
                    throw new UnauthorizedException("this request is only allowed to admin or reviewer role");
                }
            }
        }
    }

    public void OnlyAdminsOrOwnUsersData(Long id) {
        PlayerEntity oPlayerSessionEntity = oPlayerRepository.findByEmail((String) oRequest.getAttribute("player"));
        if (oPlayerSessionEntity == null) {
            throw new UnauthorizedException("no session active");
        } else {
            if (oPlayerSessionEntity.getUsertype().getId().equals(UsertypeHelper.ADMIN)) {
                
            }else if (!oPlayerSessionEntity.getId().equals(id)) {
                throw new UnauthorizedException("this request is only allowed for your own data");
            }
        }
    }

    public void OnlyOwnPlayersData(Long id) {
        String strPlayer = (String) oRequest.getAttribute("player");
        PlayerEntity oPlayerEntity = oPlayerRepository.findByEmail(strPlayer);
        if (oPlayerEntity != null) {
            if (oPlayerEntity.getUsertype().getId().equals(UsertypeHelper.PLAYER)) {
                if (!oPlayerEntity.getId().equals(id)) {
                    throw new UnauthorizedException("this request is only allowed for your own Player data");
                }
            } else {
                throw new UnauthorizedException("this request is only allowed to player role");
            }
        } else {
            throw new UnauthorizedException("this request is only allowed to player role");
        }
    }

}
