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

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import net.ausiamarch.digimondecksSB.bean.CaptchaBean;
import net.ausiamarch.digimondecksSB.bean.CaptchaResponse;
import net.ausiamarch.digimondecksSB.bean.PlayerBean;
import net.ausiamarch.digimondecksSB.exception.UnauthorizedException;
import net.ausiamarch.digimondecksSB.entity.PlayerEntity;
import net.ausiamarch.digimondecksSB.helper.RandomHelper;
import net.ausiamarch.digimondecksSB.helper.TipoUsuarioHelper;
import net.ausiamarch.digimondecksSB.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    PlayerRepository oPlayerRepository;

    public PlayerEntity login(@RequestBody PlayerBean oPlayerBean) {
        if (oPlayerBean.getPassword() != null) {
            PlayerEntity oPlayerEntity = oPlayerRepository.findByEmailAndPassword(oPlayerBean.getEmail(), oPlayerBean.getPassword());
            if (oPlayerEntity != null) {
                oHttpSession.setAttribute("Player", oPlayerEntity);
                return oPlayerEntity;
            } else {
                throw new UnauthorizedException("email or password incorrect");
            }
        } else {
            throw new UnauthorizedException("wrong password");
        }
    }

    public void logout() {
        oHttpSession.invalidate();
    }

    public PlayerEntity check() {
        PlayerEntity oPlayerSessionEntity = (PlayerEntity) oHttpSession.getAttribute("Player");
        if (oPlayerSessionEntity != null) {
            return oPlayerSessionEntity;
        } else {
            throw new UnauthorizedException("no active session");
        }
    }

}
