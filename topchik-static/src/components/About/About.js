import React from 'react';
import './styles.less';
import { Link } from 'react-router-dom';

export default function About() {
    return (
        <div className="about">
            <p className="about__definition">
                <b>Топчик</b> - это веб-приложение, формирующее топы разработчиков для того, чтобы
                узнать кто был продуктивнее и вносил наибольший вклад в проекты на основе данных из
                Github.
            </p>
            <p className="about__hh-school">
                Сделано в{' '}
                <a className="about__link" href="https://school.hh.ru/" target="_blank">
                    школе программистов hh.ru
                </a>
            </p>
            <p className="about__team">
                <h4>Команда проекта:</h4>
                <ul className="about__team-list">
                    <li>
                        <a
                            className="about__link"
                            href="https://github.com/ilyashubin"
                            target="_blank"
                        >
                            Илья Шубин
                        </a>
                        , ментор
                    </li>
                    <li>
                        <a
                            className="about__link"
                            href="https://github.com/svelichko"
                            target="_blank"
                        >
                            Семён Величко
                        </a>
                        , ментор
                    </li>
                    <li>
                        <a
                            className="about__link"
                            href="https://github.com/staanov"
                            target="_blank"
                        >
                            Станислав Новиков
                        </a>
                        , бэкенд
                    </li>
                    <li>
                        <a
                            className="about__link"
                            href="https://github.com/foxel93"
                            target="_blank"
                        >
                            Зотов Василий
                        </a>
                        , бэкенд
                    </li>
                    <li>
                        <a
                            className="about__link"
                            href="https://github.com/romanphilipskikh"
                            target="_blank"
                        >
                            Филипских Роман
                        </a>
                        , фронтенд
                    </li>
                </ul>
            </p>
            <p className="about__categories-text">
                <h4 className="about__heading-subtitle">Существует 7 категорий топов:</h4>
                <ol className="about__categories-list">
                    <li>Спринтеры - количество замерженных PR</li>
                    <li>Гиганты мысли - количество добавленных строк кода</li>
                    <li>Реноваторы - количество удаленных строк кода</li>
                    <li>Магистры - количество комментариев на ревью в PR других людей</li>
                    <li>Надзиратели - количество PR, в которых оставили комментарий</li>
                    <li>Покровители - количество апрувнутых PR</li>
                    <li>Добряки - быстрота апрува</li>
                </ol>
                Топы в каждой категории бывают за неделю, за квартал, за год и за всё время.
            </p>
            Просмотр топов доступен для каждой категории отдельно и для всех в сумме во вкладке
            "Глобально".
            <p className="about__scores">
                <h4 className="about__heading-subtitle">Подсчёт очков</h4>
                Очки присваиваются каждую неделю по каждой категории отдельно по всем проектам.
                Чтобы быть выше в топе нужно делать больше всех действий, релевантных для
                определенной категории. За каждую неделю можно получить от 1 (за 10 место) до 10 (за
                1 место) очков и получить медаль (за первые 3 места). В остальных категориях лучший
                определяется по очкам, которые он заработал за каждую прошедшую неделю в периоде.
            </p>
            <Link className="about__tops-link" to={'/repositories/global'}>
                Вернуться к глобальному топу
            </Link>
            <div className="about__donation">
                <iframe
                    src="https://money.yandex.ru/quickpay/shop-widget?writer=seller&targets=%D0%A1%D0%BF%D0%B0%D1%81%D0%B8%D0%B1%D0%BE%20%D0%B7%D0%B0%20%D1%82%D0%BE%D0%BF%D1%87%D0%B8%D0%BA)&targets-hint=&default-sum=100&button-text=11&payment-type-choice=on&comment=on&hint=%D0%BB%D1%8E%D0%B1%D0%B0%D1%8F%20%D0%B5%D1%80%D1%83%D0%BD%D0%B4%D0%B0%20%D0%B5%D1%81%D0%BB%D0%B8%20%D1%85%D0%BE%D1%87%D0%B5%D1%82%D1%81%D1%8F&successURL=&quickpay=shop&account=4100115246755871"
                    width="100%"
                    height="313"
                    frameborder="0"
                    allowtransparency="true"
                    scrolling="no"
                ></iframe>
            </div>
        </div>
    );
}
