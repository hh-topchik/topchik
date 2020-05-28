import React, { useEffect, useState, Fragment } from 'react';
import './styles.less';
import { useSelector, useDispatch } from 'react-redux';
import { fetchContributorStatisticsByRepositoryId } from '../../redux/contributorsStatistics/contributorsStatisticsActions';
import { useParams, useLocation } from 'react-router-dom';
import ContributorSelect from '../ContributorSelect/ContributorSelect';
import ContributorDetailInfo from '../ContributorDetailInfo/ContributorDetailInfo';
import Chart from '../Chart/Chart';

export default function ContributorsStatistics() {
    const { contributors, contributorsStatistics, categories } = useSelector((state) => ({
        contributors: state.contributors,
        contributorsStatistics: state.contributorsStatistics,
        categories: state.categories,
    }));

    const repositoryId = useParams().repositoryId;
    const initialAccount = useLocation().state.account;
    const initialCategoryId = useLocation().state.categoryId;

    const [activeAccount, setActiveAccount] = useState(initialAccount);
    const [activeCategoryId, setActiveCategoryId] = useState(initialCategoryId);

    const dispatch = useDispatch();
    const getContributorStatisticsByRepoId = (repositoryId, accountId) =>
        dispatch(fetchContributorStatisticsByRepositoryId(repositoryId, accountId));

    const currentAccountStatistics = contributorsStatistics.filter(
        (statistics) =>
            statistics.repositoryId === repositoryId && statistics.contributor === activeAccount,
    );

    const currentContributors = contributors
        .filter((repositoryContributors) => repositoryContributors.repositoryId === repositoryId)
        .map((repositoryContributors) => repositoryContributors.contributors);

    const getContributorStatistics = (repositoryId, activeAccount) => {
        const needRequestStatistics =
            contributorsStatistics.length === 0 || currentAccountStatistics.length === 0;

        if (needRequestStatistics) {
            const accountId = currentContributors[0].filter((contributor) => {
                return contributor.account === activeAccount;
            })[0].id;
            getContributorStatisticsByRepoId(repositoryId, accountId);
        }
    };

    useEffect(() => {
        getContributorStatistics(repositoryId, activeAccount);
    }, [activeAccount]);

    return (
        <div className="contributors-statistics">
            <h3 className="contributors-statistics__heading">Вклад в развитие проекта</h3>
            {!!currentContributors[0] && currentAccountStatistics[0] ? (
                <Fragment>
                    <ContributorSelect
                        contributors={currentContributors[0]}
                        activeAccount={activeAccount}
                        onChange={setActiveAccount}
                    />
                    <ContributorDetailInfo
                        account={activeAccount}
                        avatar={
                            currentContributors[0].filter(
                                (contributor) => contributor.account === activeAccount,
                            )[0].avatar
                        }
                        goldCount={currentAccountStatistics[0].medals.gold}
                        silverCount={currentAccountStatistics[0].medals.silver}
                        bronzeCount={currentAccountStatistics[0].medals.bronze}
                    />
                    <ChartTypeSelector
                        categories={categories}
                        activeCategoryId={activeCategoryId}
                        onChange={setActiveCategoryId}
                    />
                    <Chart
                        data={currentAccountStatistics[0].categoriesStatistics}
                        activeCategoryId={activeCategoryId}
                        dataKeyX={'week'}
                        dataKeyY={'value'}
                    />
                </Fragment>
            ) : null}
        </div>
    );
}

function ChartTypeSelector({ categories, activeCategoryId, onChange }) {
    return (
        <div className="chart-select">
            <label className="chart-select__label" htmlFor="chart-select__select">
                <select
                    className="chart-select__select"
                    value={activeCategoryId}
                    onChange={(e) => onChange(e.target.value)}
                >
                    {categories.map((category, index) => (
                        <option value={category.id} key={index}>
                            {category.description}
                        </option>
                    ))}
                </select>
            </label>
        </div>
    );
}

function StatisticsSummary({ count, unitOfMeasure, points }) {
    return (
        <div className="statistics-summary">{`Всего: ${count} ${unitOfMeasure} (${points} оч.)`}</div>
    );
}
