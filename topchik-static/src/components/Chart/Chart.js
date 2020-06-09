import React from 'react';
import classNames from 'classnames';
import './styles.less';
import {
    LineChart,
    Line,
    XAxis,
    YAxis,
    Tooltip,
    ResponsiveContainer,
    CartesianGrid,
} from 'recharts';

export default function Chart({ data, activeCategoryId, dataKeyX, dataKeyY }) {
    const currentData = data.filter((item) => item.categoryId == activeCategoryId);

    return currentData.length === 0 ? (
        <div className="chart">Данные по выбранной категории отсутствуют</div>
    ) : (
        <div className="chart">
            <StatisticsSummary count={currentData[0].count} />
            <ResponsiveContainer>
                <LineChart
                    data={currentData[0].date}
                    margin={{ top: 5, right: 30, left: 0, bottom: 5 }}
                >
                    <XAxis dataKey={dataKeyX} />
                    <YAxis />
                    <Tooltip content={<CustomTooltip />} />
                    <CartesianGrid strokeDasharray="3 3" />
                    <Line
                        type="monotone"
                        dataKey={dataKeyY}
                        stroke="#0f8fee"
                        activeDot={{ r: 8 }}
                    />
                </LineChart>
            </ResponsiveContainer>
        </div>
    );
}

function CustomTooltip(props) {
    const { active, payload } = props;
    return active ? (
        <div className="custom-tooltip">
            <div className="custom-tooltip__date">{payload[0].payload.week}</div>
            <div className="custom-tooltip__count">{payload[0].payload.value}</div>

            {!!payload[0].payload.medal ? (
                <div className="custom-tooltip__medals">
                    {payload[0].payload.medal.map((medal, index) => {
                        if (medal > 0) {
                            return (
                                <i
                                    key={index}
                                    className={classNames('fas fa-medal custom-tooltip__medal', {
                                        gold: medal === 1,
                                        silver: medal === 2,
                                        bronze: medal === 3,
                                    })}
                                ></i>
                            );
                        }
                    })}
                </div>
            ) : null}
        </div>
    ) : null;
}

function StatisticsSummary({ count }) {
    return <div className="statistics-summary">{`Сумма за всё время: ${count}`}</div>;
}
